/*******************************************************************************
 * (c) Copyright 2017 EntIT Software LLC, a Micro Focus company
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal in the Software without restriction, including without 
 * limitation the rights to use, copy, modify, merge, publish, distribute, 
 * sublicense, and/or sell copies of the Software, and to permit persons to 
 * whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY 
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 ******************************************************************************/
package com.fortify.processrunner.context;

import java.util.Collection;

import com.fortify.processrunner.cli.CLIOptionDefinitions;

/**
 * This interface provides the {@link #generateContexts(Context)} method for 
 * generating a collection of {@link Context} instances based on a given 
 * initial {@link Context}. The {@link #isContextGeneratorEnabled()} method
 * allows for specifying whether this {@link IContextGenerator} instance is
 * enabled. Usually only one instance may be enabled at any given time. 
 * 
 * @author Ruud Senden
 *
 */
public interface IContextGenerator {
	/**
	 * Generate a {@link Collection} of {@link Context} instances, based on the 
	 * given initialContext
	 * @param initialContext
	 * @return
	 */
	public Collection<Context> generateContexts(Context initialContext);

	/**
	 * Update the given {@link CLIOptionDefinitions} with mapping information
	 * @param optionDefinitions
	 */
	public void updateCLIOptionDefinitionsDefaultValueDescriptions(CLIOptionDefinitions optionDefinitions);
	
	/**
	 * Get additional allowed source for {@link CLIOptionDefinitions}
	 * @return
	 */
	public String getCLIOptionDefinitionAllowedSource();
}
