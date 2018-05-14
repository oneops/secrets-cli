/**
 * *****************************************************************************
 *
 * <p>Copyright 2017 Walmart, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * <p>*****************************************************************************
 */
package com.oneops.secrets.command;

import static com.oneops.secrets.utils.Color.yellow;
import static com.oneops.secrets.utils.Common.println;
import static java.nio.file.StandardOpenOption.READ;

import com.oneops.secrets.config.CliConfig;
import io.airlift.airline.Command;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * Command to tail secrets cli logs.
 *
 * @author Suresh
 */
@Command(name = "log", description = "Tail (no-follow) secrets cli log file.")
public class TailLog implements Runnable {

  @Override
  public void run() {
    Path logPath = Paths.get(CliConfig.logPath.toString().replace("%g", "0"));
    println(yellow("Log file : " + logPath));

    try (SeekableByteChannel sbc = Files.newByteChannel(logPath, READ)) {
      long fileSize = sbc.size();
      int tailSize = 2048;
      long pos = fileSize > tailSize ? fileSize - tailSize : 0;

      ByteBuffer buf = ByteBuffer.allocate(tailSize);
      sbc.position(pos);
      while (sbc.read(buf) > 0) {
        buf.rewind();
        System.out.print(Charset.forName("UTF-8").decode(buf));
        buf.flip();
      }
    } catch (IOException ex) {
      throw new IllegalStateException("Can't tail secrets cli log file.", ex);
    }
  }
}
