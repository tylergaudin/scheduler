package gaudin.scheduler.utilities;

import javafx.event.ActionEvent;

import java.io.IOException;

/**This is the functional interface for my LambdaNavigate Lambdas.
 */
@FunctionalInterface
public interface LambdaNavigate {

    void navToScene (String string, ActionEvent event) throws IOException;

}
