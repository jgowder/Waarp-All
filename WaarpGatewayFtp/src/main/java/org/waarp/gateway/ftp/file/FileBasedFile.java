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

package org.waarp.gateway.ftp.file;

import org.waarp.common.command.exception.CommandAbstractException;
import org.waarp.ftp.core.file.FtpFile;
import org.waarp.ftp.core.session.FtpSession;
import org.waarp.ftp.filesystembased.FilesystemBasedFtpFile;

import java.io.File;

/**
 * FtpFile implementation based on true directories and files
 */
public class FileBasedFile extends FilesystemBasedFtpFile {
  /**
   * @param session
   * @param fileBasedDir It is not necessary the directory that owns
   *     this
   *     file.
   * @param path
   * @param append
   *
   * @throws CommandAbstractException
   */
  public FileBasedFile(final FtpSession session,
                       final FileBasedDir fileBasedDir, final String path,
                       final boolean append) throws CommandAbstractException {
    super(session, fileBasedDir, path, append);
  }

  /**
   * This method is a good to have in a true {@link FtpFile} implementation.
   *
   * @return the File associated with the current FtpFile operation
   */
  public final File getTrueFile() {
    try {
      return getFileFromPath(getFile());
    } catch (final CommandAbstractException e) {
      return null;
    }
  }
}
