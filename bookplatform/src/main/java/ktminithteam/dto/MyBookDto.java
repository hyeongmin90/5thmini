package ktminithteam.dto;

import java.util.Date;

public class MyBookDto {
    private String title;
    private Long authorId;
    private Date expirationDate;
    private String content;
    private String coverUrl;
    private String summaryUrl;
    private String category;

    public MyBookDto(
        String title,
        Long authorId,
        Date expirationDate,
        String content,
        String coverUrl,
        String summaryUrl,
        String category
    ) {
        this.title = title;
        this.authorId = authorId;
        this.expirationDate = expirationDate;
        this.content = content;
        this.coverUrl = coverUrl;
        this.summaryUrl = summaryUrl;
        this.category = category;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getAuthorId() { return authorId; }
    public void setAuthor(Long authorId) { this.authorId = authorId; }
    public Date getExpireDate() { return expirationDate; }
    public void setExpireDate(Date expirationDate) { this.expirationDate = expirationDate; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public String getSummaryUrl() { return summaryUrl; }
    public void setSummaryUrl(String summaryUrl) { this.summaryUrl = summaryUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
