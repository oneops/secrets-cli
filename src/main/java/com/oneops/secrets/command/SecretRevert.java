/*******************************************************************************
 *
 *   Copyright 2017 Walmart, Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *******************************************************************************/
package com.oneops.secrets.command;

import com.oneops.secrets.proxy.SecretsProxyException;
import com.oneops.secrets.proxy.model.Result;
import io.airlift.airline.*;

import java.io.IOException;

import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

/**
 * Secret revert command.
 *
 * @author Suresh
 */
@Command(name = "revert", description = "Revert secret to the given version index.")
public class SecretRevert extends SecretsCommand {

    @Arguments(title = "Secret name", description = "Secrets name", required = true)
    public String secretName;

    @Option(name = "-i", title = "Version index", description = "Secret version index.", required = true)
    public long version;

    @Override
    public void exec() {
        try {
            String in = System.console().readLine(warn(String.format("You're going to reset the current version index of the secret to %d. Do you want to proceed (y/n)? ", version)));
            if (in == null || !in.equalsIgnoreCase("y")) {
                throw new IllegalStateException("Exiting");
            }

            Result<Void> result = secretsClient.setSecretVersion(app.getName(), secretName, version);
            if (result.isSuccessful()) {
                StringBuilder buf = new StringBuilder();
                String lineSep = System.lineSeparator();
                buf.append(sux(format("Reverted the secret '%s' version to '%d' for application %s.", secretName, version, app.getNsPath())))
                        .append(lineSep)
                        .append(lineSep)
                        .append("Note the followings,")
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot(String.format("Secret '%s' version '%d' will be synced to '%s' env computes in few seconds.", secretName, version, app.getNsPath().toLowerCase()))))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot(String.format("Applications can access secret content by reading '/secrets/%s' file.", secretName))))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot("You may need to restart the application inorder for this secret change to take effect.")))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot("You can revert back to previous version of the secret. Check 'secrets help revert' for more details.")))
                        .append(lineSep);
                println(buf.toString());

            } else {
                throw new SecretsProxyException(this, result.getErr());
            }

        } catch (IOException e) {
            throw new SecretsProxyException(e);
        }
    }
}
