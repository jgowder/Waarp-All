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
package org.waarp.ftp.core.control;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import org.waarp.common.command.ReplyCode;
import org.waarp.common.utility.WaarpNettyUtil;
import org.waarp.common.utility.WaarpStringUtils;
import org.waarp.ftp.core.config.FtpConfiguration;
import org.waarp.ftp.core.session.FtpSession;

/**
 * Pipeline factory for Control command connection
 */
public class FtpInitializer extends ChannelInitializer<SocketChannel> {
  /**
   * CRLF, CRNUL, LF delimiters
   */
  protected static final ByteBuf[] delimiter = {
      WaarpNettyUtil.wrappedBuffer(
          ReplyCode.CRLF.getBytes(WaarpStringUtils.UTF8)),
      WaarpNettyUtil.wrappedBuffer(
          ReplyCode.CRNUL.getBytes(WaarpStringUtils.UTF8)),
      WaarpNettyUtil.wrappedBuffer(ReplyCode.LF.getBytes(WaarpStringUtils.UTF8))
  };

  protected static final FtpControlStringDecoder ftpControlStringDecoder =
      new FtpControlStringDecoder(WaarpStringUtils.UTF8);

  protected static final FtpControlStringEncoder ftpControlStringEncoder =
      new FtpControlStringEncoder(WaarpStringUtils.UTF8);

  /**
   * Business Handler Class if any (Target Mode only)
   */
  protected final Class<? extends BusinessHandler> businessHandler;

  /**
   * Configuration
   */
  protected final FtpConfiguration configuration;

  /**
   * Constructor which Initializes some data for Server only
   *
   * @param businessHandler
   * @param configuration
   */
  public FtpInitializer(final Class<? extends BusinessHandler> businessHandler,
                        final FtpConfiguration configuration) {
    this.businessHandler = businessHandler;
    this.configuration = configuration;
  }

  /**
   * Create the pipeline with Handler, ObjectDecoder, ObjectEncoder.
   */
  @Override
  public void initChannel(final SocketChannel ch) throws Exception {
    final ChannelPipeline pipeline = ch.pipeline();
    // Add the text line codec combination first,
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, delimiter));
    pipeline.addLast("decoder", ftpControlStringDecoder);
    pipeline.addLast("encoder", ftpControlStringEncoder);
    // Threaded execution for business logic
    final EventExecutorGroup executorGroup =
        configuration.getFtpInternalConfiguration().getExecutor();
    // and then business logic. New one on every connection
    final BusinessHandler newbusiness =
        businessHandler.getDeclaredConstructor().newInstance();
    final NetworkHandler newNetworkHandler =
        new NetworkHandler(new FtpSession(configuration, newbusiness));
    pipeline.addLast(executorGroup, "handler", newNetworkHandler);
  }
}
