package com.lms.course.dto;

import jakarta.validation.constraints.*;

public class CourseOutlineDTO {
    
   private Long id;
    
    @NotBlank(message = "Outline title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;
    
    private String description;
    
    @NotNull(message = "Order index is required")
    @Min(value = 1, message = "Order index must be at least 1")
    private Integer orderIndex;
    
    @NotNull(message = "Content type is required")
    private String contentType;
    
    @Size(max = 255, message = "Content URL must not exceed 255 characters")
    private String contentUrl;
    
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    // Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Integer getOrderIndex() {return orderIndex;}
    public void setOrderIndex(Integer orderIndex) {this.orderIndex = orderIndex;}
    public String getContentType() {return contentType;}
    public void setContentType(String contentType) {this.contentType = contentType;}
    public String getContentUrl() {return contentUrl;}
    public void setContentUrl(String contentUrl) {this.contentUrl = contentUrl;}
    public Integer getDuration() {return duration;}
    public void setDuration(Integer duration) {this.duration = duration;}
}
