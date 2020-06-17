package fr.excilys.dto;



public class RequestParamDto {
	private String pageIterator;
	private String taillePage;
	private String orderBy;
	private String nbPages;
	private String searchForm;
	public RequestParamDto(String pageIterator, String taillePage, String orderBy, String nbPages,
			String searchForm) {
		super();
		this.pageIterator = pageIterator;
		this.taillePage = taillePage;
		this.orderBy = orderBy;
		this.nbPages = nbPages;
		this.searchForm = searchForm;
	}
	public String getPageIterator() {
		return pageIterator;
	}
	public void setPageIterator(String pageIterator) {
		this.pageIterator = pageIterator;
	}
	public String getTaillePage() {
		return taillePage;
	}
	public void setTaillePage(String taillePage) {
		this.taillePage = taillePage;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getNbPages() {
		return nbPages;
	}
	public void setNbPages(String nbPages) {
		this.nbPages = nbPages;
	}
	public String getSearch() {
		return searchForm;
	}
	public void setSearch(String searchForm) {
		this.searchForm = searchForm;
	}
	
	
	
	
}
