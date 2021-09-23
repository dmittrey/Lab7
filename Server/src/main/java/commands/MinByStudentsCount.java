package commands;

import utility.*;

/**
 * Class print object form collection
 */
public class MinByStudentsCount extends CommandAbstract {

    private final Receiver receiver;

    public MinByStudentsCount(Receiver aReceiver) {
        super("min_by_students_count", "print any object from the collection whose " +
                "studentsCount field value is minimal");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        return receiver.minByStudentsCount();
    }
}