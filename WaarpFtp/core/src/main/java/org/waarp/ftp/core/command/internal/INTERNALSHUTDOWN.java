/*
 * This file is part of Waarp Project (named also Waarp or GG).
 *
 *  Copyright (c) 2019, Waarp SAS, and individual contributors by the @author
 *  tags. See the COPYRIGHT.txt in the distribution for a full listing of
 * individual contributors.
 *
 *  All Waarp Project is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Waarp is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 * Waarp . If not, see <http://www.gnu.org/licenses/>.
 */
package org.waarp.ftp.core.command.internal;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.waarp.common.command.ReplyCode;
import org.waarp.common.command.exception.Reply500Exception;
import org.waarp.common.command.exception.Reply501Exception;
import org.waarp.common.crypto.ssl.WaarpSslUtility;
import org.waarp.common.logging.WaarpLogger;
import org.waarp.common.logging.WaarpLoggerFactory;
import org.waarp.ftp.core.command.AbstractCommand;
import org.waarp.ftp.core.config.FtpConfiguration;
import org.waarp.ftp.core.utils.FtpChannelUtils;

/**
 * Internal shutdown command that will shutdown the FTP service with a password
 */
public class INTERNALSHUTDOWN extends AbstractCommand {
  /**
   * Internal Logger
   */
  private static final WaarpLogger logger =
      WaarpLoggerFactory.getLogger(INTERNALSHUTDOWN.class);

  /**
   *
   */
  private static class ShutdownChannelFutureListener
      implements ChannelFutureListener {

    private final FtpConfiguration configuration;

    protected ShutdownChannelFutureListener(
        final FtpConfiguration configuration) {
      this.configuration = configuration;
    }

    @Override
    public final void operationComplete(final ChannelFuture arg0) {
      WaarpSslUtility.closingSslChannel(arg0.channel());
      FtpChannelUtils.teminateServer(configuration);
    }

  }

  @Override
  public final void exec() throws Reply501Exception, Reply500Exception {
    if (!getSession().getAuth().isAdmin()) {
      // not admin
      throw new Reply500Exception("Command Not Allowed");
    }
    if (!hasArg()) {
      throw new Reply501Exception("Shutdown Need password");
    }
    final String password = getArg();
    if (logger.isDebugEnabled()) {
      logger.debug("{} {}", password,
                   getConfiguration().checkPassword(password));
    }
    if (!getConfiguration().checkPassword(password)) {
      throw new Reply501Exception("Shutdown Need a correct password");
    }
    logger.warn("Shutdown...");
    getSession().setReplyCode(ReplyCode.REPLY_221_CLOSING_CONTROL_CONNECTION,
                              "System shutdown");
    getSession().getNetworkHandler().writeIntermediateAnswer().addListener(
        new ShutdownChannelFutureListener(getConfiguration()));
  }

}
