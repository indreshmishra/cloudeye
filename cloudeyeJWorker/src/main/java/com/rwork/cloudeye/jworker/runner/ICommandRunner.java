package com.rwork.cloudeye.jworker.runner;

import java.io.IOException;

import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandOutput;

public interface ICommandRunner {
	
	public CommandOutput runCommand(CommandHost ch) throws IOException;

}
