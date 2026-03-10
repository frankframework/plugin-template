/*
   Copyright 2026 WeAreFrank!

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

import java.io.Closeable;
import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.pf4j.Plugin;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Contains a Spring applicationContext to be able to test the life-cycle methods, as well as the Spring Context hierarchy.
 *
 * @author Niels Meijer
 */
public class MainClass extends Plugin implements ApplicationContextAware, InitializingBean, Closeable {
	private GenericApplicationContext applicationContext;

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
		this.applicationContext = new GenericApplicationContext();
		this.applicationContext.setParent(applicationContext);
	}

	/**
	 * Should only be called once, throws an IllegalStateException when refreshed multiple times.
	 * See {@link GenericApplicationContext#refreshBeanFactory() }.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		applicationContext.refresh();
	}

	/**
	 * Fires an ContextStartedEvent
	 */
	@Override
	public void start() {
		applicationContext.start();
	}

	/**
	 * Fires an ContextStoppedEvent
	 */
	@Override
	public void stop() {
		applicationContext.stop();
	}

	@Override
	public void close() throws IOException {
		applicationContext.close();
		applicationContext = null;
	}
}
