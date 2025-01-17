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
package org.waarp.ftp.core.command.rfc4217;

import org.waarp.common.command.ReplyCode;
import org.waarp.common.command.exception.CommandAbstractException;
import org.waarp.common.command.exception.Reply500Exception;
import org.waarp.common.command.exception.Reply534Exception;
import org.waarp.ftp.core.command.AbstractCommand;

/**
 * CCC command
 */
public class CCC extends AbstractCommand {

  @Override
  public final void exec() throws CommandAbstractException {
    if (!getSession().getConfiguration().getFtpInternalConfiguration()
                     .isAcceptAuthProt()) {
      throw new Reply534Exception("CCC not supported");
    }
    if (!getSession().isSslReady()) {
      // Not SSL
      throw new Reply500Exception("Session already not using SSL / TLS");
    }
    getSession().setSsl(false);
    getSession().setReplyCode(ReplyCode.REPLY_200_COMMAND_OKAY, null);
  }

}
