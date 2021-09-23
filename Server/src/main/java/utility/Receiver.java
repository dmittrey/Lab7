package utility;

import Database.DBWorker;
import commands.CommandAbstract;
import data.StudyGroup;

import java.util.Deque;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Receiver {

    private final CollectionManager collectionManager;
    private final DBWorker dbWorker;
    private final Map<String, CommandAbstract> commands;
    private final Deque<String> previousCommands;

    public Receiver(CollectionManager aCollectionManager, DBWorker aDBWorker, Map<String, CommandAbstract> aCommands,
                    Deque<String> aPreviousCommands) {
        collectionManager = aCollectionManager;
        dbWorker = aDBWorker;
        commands = aCommands;
        previousCommands = aPreviousCommands;
    }

    public Response help() {
        StringBuilder sb = new StringBuilder();
        sb.append(TextFormatting.getBlueText("\nList of commands:\n\n"));

        commands.keySet().stream().filter(command -> !command.equals("save")).
                map(command -> "\t" + commands.get(command).getDescription() + "\n\n").
                forEach(sb::append);
        sb.append("\t")
                .append("execute_script : Read and execute script from entered file")
                .append(TextFormatting.getBlueText("\n\tYou should to enter script name after entering a command"))
                .append("\n\n");
        sb.append("\t")
                .append("exit : end the program (without saving it to a file)")
                .append("\n\n");

        return new Response(sb.toString());
    }

    public Response info() {

        return new Response(TextFormatting.getBlueText("\nInformation about collection:\n")
                + collectionManager.getInfo());
    }

    public Response show() {
        if (collectionManager.getCollection().size() == 0)
            return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        return new Response(collectionManager.getCollection());
    }

    public Response add(Request aCommand) {

        StudyGroup studyGroup = aCommand.getCommand().getStudyGroup();

        collectionManager.add(studyGroup);
        // TODO: 23/09/2021 Че за херня, почему мы всегда можем добавить?

        return new Response(TextFormatting.getGreenText("\n\tStudy group has been added!\n"));
    }

    public Response updateId(Request aCommand) {

        String anArg = aCommand.getCommand().getArg();
        StudyGroup upgradedGroup = aCommand.getCommand().getStudyGroup();

        StudyGroup studyGroup = collectionManager.getId(Integer.parseInt(anArg));

        if (studyGroup != null) collectionManager.remove(studyGroup);
        else {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tAn object with this id does not exist!\n"));
        }

        upgradedGroup.setId(Integer.parseInt(anArg));
        collectionManager.add(upgradedGroup);

        return new Response(TextFormatting.getGreenText("\n\tObject has been updated!\n"));
    }

    public Response removeById(Request aCommand) {

        String anArg = aCommand.getCommand().getArg();
        Integer anId = Integer.parseInt(anArg);

        if (!collectionManager.remove(collectionManager.getId(anId))) {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tAn object with this id does not exist!\n"));
        }

        return new Response(TextFormatting.getGreenText("\n\tObject has been removed!\n"));
    }

    public Response clear() {
        collectionManager.clear();

        return new Response(TextFormatting.getGreenText("\n\tSuccessful!\n"));
    }

    public Response save() {
//        fileWorker.saveToXml().toString();
        // TODO: 23/09/2021 Поменять на работу с бд

        return new Response("");
    }

    public Response addIfMax(Request aCommand) {

        StudyGroup studyGroup = aCommand.getCommand().getStudyGroup();

        if (collectionManager.getMax() != null && studyGroup.compareTo(collectionManager.getMax()) <= 0) {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tStudy group isn't worst!\n"));
        }

        collectionManager.add(studyGroup);
        return new Response(TextFormatting.getGreenText("\n\n\tStudy group has been added!\n"));
    }

    public Response addIfMin(Request aRequest) {

        StudyGroup studyGroup = aRequest.getCommand().getStudyGroup();

        if (collectionManager.getMin() != null && studyGroup.compareTo(collectionManager.getMin()) >= 0) {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tStudy group isn't worst!\n"));
        }

        collectionManager.add(studyGroup);
        return new Response(TextFormatting.getGreenText("\n\n\tStudy group has been added!\n"));
    }

    public Response history() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        previousCommands.stream().map(command -> ")" + command + "\n").forEach(sb::append);

        return new Response(sb.toString());
    }

    public Response minByStudentsCount() {

        if (collectionManager.getMinStudentsCount() != null)
            return new Response(collectionManager.getMinStudentsCount());
        else
            return new Response(TextFormatting.getRedText("\n\tThere are no study groups in the collection yet!\n"));
    }

    public Response countLessThanStudentsCount(Request aRequest) {

        String anArg = aRequest.getCommand().getArg();

        if (collectionManager.getCollection().size() == 0)
            return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        long count = collectionManager.getCollection().stream().filter(studyGroup ->
                studyGroup.getStudentsCount() < Integer.parseInt(anArg)).count();

        return new Response(TextFormatting.getGreenText("\n\tAmount of elements: " + count + "\n"));
    }

    public Response filterStartsWithName(Request aCommand) {

        String anArg = aCommand.getCommand().getArg();
        Set<StudyGroup> collection = collectionManager.getCollection();

        if (collection.size() == 0) return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        Set<StudyGroup> groups = collection.stream().
                filter(studyGroup -> studyGroup.getName().startsWith(anArg)).
                collect(Collectors.toSet());

        if (groups.isEmpty()) return new Response(TextFormatting.getRedText("\n\tNo objects found!\n"));

        return new Response(groups);
    }


}
