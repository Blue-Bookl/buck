/*
 * Copyright 2018-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.rules.modern.impl;

import com.facebook.buck.rules.BuildRule;
import com.facebook.buck.rules.SourcePath;
import com.facebook.buck.rules.modern.InputRuleResolver;
import com.facebook.buck.rules.modern.OutputPath;
import java.util.function.Consumer;

/** Computes all the deps by visiting all referenced SourcePaths. */
public class DepsComputingVisitor extends AbstractValueVisitor<RuntimeException> {
  private final InputRuleResolver inputRuleResolver;
  private final Consumer<BuildRule> depsBuilder;

  public DepsComputingVisitor(
      InputRuleResolver inputRuleResolver, Consumer<BuildRule> depsBuilder) {
    this.inputRuleResolver = inputRuleResolver;
    this.depsBuilder = depsBuilder;
  }

  @Override
  public void visitOutputPath(OutputPath value) {}

  @Override
  public void visitSourcePath(SourcePath value) {
    inputRuleResolver.resolve(value).ifPresent(depsBuilder);
  }

  @Override
  public void visitSimple(Object value) {}
}
