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
package org.waarp.ftp.core.command.rfc2428;

import org.waarp.common.command.ReplyCode;
import org.waarp.common.command.exception.Reply425Exception;
import org.waarp.common.logging.WaarpLogger;
import org.waarp.common.logging.WaarpLoggerFactory;
import org.waarp.ftp.core.command.AbstractCommand;
import org.waarp.ftp.core.config.FtpInternalConfiguration;
import org.waarp.ftp.core.data.FtpDataAsyncConn;
import org.waarp.ftp.core.utils.FtpChannelUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * EPSV command
 */
public class EPSV extends AbstractCommand {
  /**
   * Internal Logger
   */
  private static final WaarpLogger logger =
      WaarpLoggerFactory.getLogger(EPSV.class);

  @Override
  public final void exec() throws Reply425Exception {
    // No Check if any argument
    // Take a new port: 3 attempts
    boolean isInit = false;
    int newport = -1;
    if (getSession().getDataConn().isPassiveMode()) {
      // Previous mode was Passive so remove the current configuration
      final InetSocketAddress local =
          getSession().getDataConn().getLocalAddress();
      final InetAddress remote =
          getSession().getDataConn().getRemoteAddress().getAddress();
      getConfiguration().delFtpSession(remote, local);
    }
    for (int i = 1; i <= FtpInternalConfiguration.RETRYNB; i++) {
      newport = FtpDataAsyncConn.getNewPassivePort(getConfiguration());
      if (newport == -1) {
        throw new Reply425Exception("No port available");
      }
      logger.info("EPSV: set Passive Port {}", newport);
      getSession().getDataConn().setLocalPort(newport);
      getSession().getDataConn().setPassive();
      // Init the connection
      try {
        if (getSession().getDataConn().initPassiveConnection()) {
          isInit = true;
          break;
        }
      } catch (final Reply425Exception e) {
        logger.warn(
            "EPSV refused at try: " + i + " with port: " + newport + " : {}",
            e.getMessage());
      }
    }
    if (!isInit) {
      throw new Reply425Exception("Extended Passive mode not started");
    }
    // Return the address in Ftp format
    final InetSocketAddress local =
        getSession().getDataConn().getLocalAddress();
    final String slocal = "Entering Extended Passive Mode (" +
                          FtpChannelUtils.get2428Address(local) + ')';
    final InetAddress remote =
        getSession().getDataConn().getRemoteAddress().getAddress();
    // Add the current FtpSession into the reference of session since the
    // client will open the connection
    getConfiguration().setNewFtpSession(remote, local, getSession());
    getSession().setReplyCode(ReplyCode.REPLY_229_ENTERING_PASSIVE_MODE,
                              "Entering Extended Passive Mode (|||" + newport +
                              "|)");
    logger.info("EPSV: answer ready on {}", slocal);
    /*
     * Could be:this.getFtpSession().setReplyCode(ReplyCode. REPLY_229_ENTERING_PASSIVE_MODE, slocal)
     */
  }

}
