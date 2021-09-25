package utility;

public class Animator {

    private static Animator instance;

    private Animator() {
    }

    public static Animator getInstance() {
        if (instance == null) instance = new Animator();
        return instance;
    }

    public String animate(Response aResponse) {
        if (!aResponse.getResponseStatus().equals(TypeOfAnswer.SUCCESSFUL))
            return aResponse.getResponseStatus().toString();

        return aResponse.toString();
    }
}
