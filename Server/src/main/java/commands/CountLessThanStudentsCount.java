package commands;

import utility.*;

/**
 * Class for count elements whose less than specified amount of students count
 */
public class CountLessThanStudentsCount extends CommandAbstract {

    private final Receiver receiver;

    public CountLessThanStudentsCount(Receiver aReceiver) {
        super("count_less_than_students_count", "print the number of elements whose "
                + "studentsCount field value is less than the specified one" +
                TextFormatting.getBlueText("\n\tYou should to enter students count after entering a command"));
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        Integer count = Integer.valueOf(aRequest.getCommand().getArg());
        return receiver.countLessThanStudentsCount(count, username);
    }
}