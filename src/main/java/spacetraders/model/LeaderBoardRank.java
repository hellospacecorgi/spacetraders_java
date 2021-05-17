package spacetraders.model;

public class LeaderBoardRank {
    private Integer networth;
    private Integer rank;
    private String username;

    public LeaderBoardRank(Integer networth, Integer rank, String username) {
        this.networth = networth;
        this.rank = rank;
        this.username = username;
    }

    public Integer getNetworth() {
        return networth;
    }

    public Integer getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }


}
