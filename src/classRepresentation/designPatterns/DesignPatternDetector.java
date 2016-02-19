/**
 * Interface for our DesignPatternDetectors
 */
package classRepresentation.designPatterns;

import java.util.List;

import gui.DesignPatternInstance;

/**
 * @author shellajt
 *
 */
public interface DesignPatternDetector {

	public void detectPattern(String[] args, List<DesignPatternInstance> instances);
}
