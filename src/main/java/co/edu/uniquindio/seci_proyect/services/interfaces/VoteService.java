package co.edu.uniquindio.seci_proyect.services.interfaces;

public interface VoteService {
    void addVote(String reportId, String userId);
    void removeVote(String voteId);
    int countVotesByReport(String reportId);
    boolean hasUserVoted(String reportId, String userId);
}
