package hudson.plugins.parameterizedtrigger.matrix;

import hudson.Extension;
import hudson.ExtensionList;
import hudson.ExtensionPoint;
import hudson.Util;
import hudson.matrix.MatrixBuild;
import hudson.matrix.Axis;
import hudson.matrix.AxisList;
import hudson.matrix.Combination;
import hudson.matrix.MatrixConfiguration;
import hudson.model.Hudson;

/**
 * {@link MatrixBuildListener} that chooses the subset to run based on {@link MatrixSubsetAction}
 * @author Kohsuke Kawaguchi
 */
@Extension
public class MatrixBuildListenerImpl implements ExtensionPoint{
	
    public boolean doBuildConfiguration(MatrixBuild b, MatrixConfiguration c) {
        MatrixSubsetAction a = b.getAction(MatrixSubsetAction.class);
        if (a==null)    return true;

        // run the filter and restrict the subset to run
        return c.getCombination().evalScriptExpression(b.getParent().getAxes(), a.getFilter());
    }
    
    public static ExtensionList<MatrixBuildListenerImpl> all() {
    	return Hudson.getInstance().getExtensionList(MatrixBuildListenerImpl.class);
    }
}
