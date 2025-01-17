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

package org.waarp.ftp.client.transaction;

import org.waarp.common.logging.WaarpLogger;
import org.waarp.common.logging.WaarpLoggerFactory;
import org.waarp.ftp.client.WaarpFtp4jClient;

import java.io.File;

/**
 * FTP Client using FTP4J with the test scenario
 */
public class Ftp4JClientTransactionTest extends WaarpFtp4jClient {
  /**
   * Internal Logger
   */
  protected static WaarpLogger logger =
      WaarpLoggerFactory.getLogger(Ftp4JClientTransactionTest.class);

  /**
   * @param server
   * @param port
   * @param username
   * @param passwd
   * @param account
   */
  public Ftp4JClientTransactionTest(String server, int port, String username,
                                    String passwd, String account, int isSsl) {
    super(server, port, username, passwd, account, false, isSsl, 0, 10000);
    final File dir = new File("/tmp/GGFTP/" + username + '/' + account);
    dir.mkdirs();
  }

  /**
   * Ask to transfer a file
   *
   * @param local
   * @param remote
   * @param store
   *
   * @return True if the file is correctly transfered
   */
  public boolean transferFile(String local, String remote, boolean store) {
    final boolean status = transferFile(local, remote, store? 1 : -1);
    logger.info("Transfer {} to {} using {} is {}", local, remote,
                store? "STOR" : "RETR", status);
    return status;
  }

  @Override
  public boolean deleteFile(String remote) {
    final boolean status = super.deleteFile(remote);
    logger.info("Delete {} is {}", remote, status);
    return status;
  }
}
