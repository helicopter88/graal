/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.graalvm.compiler.truffle.runtime.debug;

import static org.graalvm.compiler.truffle.common.TruffleCompilerOptions.TraceTruffleSplitting;

import org.graalvm.compiler.truffle.common.TruffleCompilerOptions;
import org.graalvm.compiler.truffle.common.TruffleCompilerRuntime;
import org.graalvm.compiler.truffle.runtime.GraalTruffleRuntimeListener;
import org.graalvm.compiler.truffle.runtime.GraalTruffleRuntime;
import org.graalvm.compiler.truffle.runtime.OptimizedCallTarget;
import org.graalvm.compiler.truffle.runtime.OptimizedDirectCallNode;

public final class TraceSplittingListener implements GraalTruffleRuntimeListener {

    private TraceSplittingListener() {
    }

    public static void install(GraalTruffleRuntime runtime) {
        if (TruffleCompilerOptions.getValue(TraceTruffleSplitting)) {
            runtime.addListener(new TraceSplittingListener());
        }
    }

    private int splitCount;

    @Override
    public void onCompilationSplit(OptimizedDirectCallNode callNode) {
        OptimizedCallTarget callTarget = callNode.getCallTarget();
        String label = String.format("split %3s-%-4s-%-4s ", splitCount++, Integer.toHexString(callNode.getCurrentCallTarget().hashCode()), callNode.getCallCount());
        TruffleCompilerRuntime.getRuntime().logEvent(0, label, callTarget.toString(), callTarget.getDebugProperties(null));
    }

}
