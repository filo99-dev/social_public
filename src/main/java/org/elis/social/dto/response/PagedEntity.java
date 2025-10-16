package org.elis.social.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagedEntity<T> {
   private Integer totalPages;
   private Integer currentPageNumber;
   private List<T> items;
}
