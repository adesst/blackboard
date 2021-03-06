/**
 * Copyright (c) 2008-2009, Piet Blok
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the copyright holder nor the names of the
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.pbjar.jxlayer.plaf.ext.transform;

import javax.swing.JComponent;
import javax.swing.RepaintManager;

import org.jdesktop.swingx.ForwardingRepaintManager;
import org.pbjar.jxlayer.repaint.RepaintManagerProvider;
import org.pbjar.jxlayer.repaint.RepaintManagerUtils;
import org.pbjar.jxlayer.repaint.WrappedRepaintManager;

/**
 * A specialized {@link RepaintManager} that checks for every JComponent that is
 * being set dirty, if it has a JXLayer ancestor, equipped with a TransformUI.
 * In that case, the transformed region on the JXLayer is also marked dirty.
 * <p>
 * A fall back class if the {@link ForwardingRepaintManager} cannot be
 * instantiated because the SwingX packages are not on the class path.
 * </p>
 * 
 * @see RepaintManagerProvider
 * @see RepaintManagerUtils
 * @see TransformRPMSwingX
 */
@TransformRPMAnnotation
public class TransformRPMFallBack extends
	WrappedRepaintManager {

    /**
     * Sole constructor.
     * 
     * @param delegate
     *            the delegate {@link RepaintManager}
     */
    public TransformRPMFallBack(RepaintManager delegate) {
	super(delegate);
	TransformRPMImpl.hackInitialization(delegate, this);
    }

    /**
     * Delegates and then marks a JXLayer ancestor as dirty with the transformed
     * rectangle. 
     */
    @Override
    public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
	if (!TransformRPMImpl.addDirtyRegion(c, x, y, w, h, this)) {
	    super.addDirtyRegion(c, x, y, w, h);
	}
    }

}
