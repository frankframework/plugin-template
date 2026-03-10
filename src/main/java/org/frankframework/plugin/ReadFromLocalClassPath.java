/*
   Copyright 2025-2026 WeAreFrank!

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.frankframework.plugin;

import java.net.URL;

import org.frankframework.configuration.ConfigurationException;
import org.frankframework.core.PipeLineSession;
import org.frankframework.core.PipeRunException;
import org.frankframework.core.PipeRunResult;
import org.frankframework.pipes.FixedForwardPipe;
import org.frankframework.stream.Message;
import org.frankframework.stream.UrlMessage;
import org.frankframework.util.ClassLoaderUtils;

/**
 * Pipe that used to test reading a resource from the local (plugin) classpath.
 */
public class ReadFromLocalClassPath extends FixedForwardPipe {
	private final String filename = "local-file.txt";
	private URL resource;

	@Override
	public void configure() throws ConfigurationException {
		super.configure();

		URL resource;
		try {
			resource = ClassLoaderUtils.getResourceURL(this, filename);
		} catch (Throwable e) {
			throw new ConfigurationException("got exception searching for [" + filename + "]", e);
		}
		if (resource == null) {
			throw new ConfigurationException("cannot find resource [" + filename + "]");
		}
		this.resource = resource;
	}

	@Override
	public PipeRunResult doPipe(Message message, PipeLineSession session) throws PipeRunException {
		return new PipeRunResult(getSuccessForward(), new UrlMessage(resource));
	}

}
