package com.igomarcelino.demo_oauth_security.dto;

import java.util.List;

public record FeedDTO(List<FeedItemDTO> feedItemDTOList, int page, int pageSize, int totalPages, long totalElements) {
}
