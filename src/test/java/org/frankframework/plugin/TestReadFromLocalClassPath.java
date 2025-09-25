package org.frankframework.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.frankframework.core.PipeForward;
import org.frankframework.core.PipeLineSession;
import org.frankframework.core.PipeRunResult;
import org.frankframework.stream.Message;

public class TestReadFromLocalClassPath {

	@Test
	public void testSuccess() throws Exception {
		ReadFromLocalClassPath pipe = new ReadFromLocalClassPath();
		pipe.addForward(new PipeForward("success", "next"));
		pipe.configure();

		try (PipeLineSession session = new PipeLineSession()) {
			PipeRunResult res = pipe.doPipe(Message.nullMessage(), session);
			assertEquals("Niels was here!", res.getResult().asString());
		}
	}
}
