package memberMVC.ex02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
	BoardDAO boardDAO;
	public BoardService() {
		boardDAO = new BoardDAO();
	}
	
	public Map listArticles(Map<String,Integer> pagingMap) {
		Map articleMap = new HashMap();
		List<ArticleVO> articleList = boardDAO.selectAllArticles(pagingMap);
		int totArticles = boardDAO.selectToArticles();
		articleMap.put("articleList", articleList);
		articleMap.put("totArticles", totArticles);
		return articleMap;
	}
	
	public List<ArticleVO> listArticles() {
		List<ArticleVO> articleList = boardDAO.selectAllArticles();
		return articleList;
	}
	
	public int addArticle(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
	
	public ArticleVO viewArticle(int article_no){
		ArticleVO article = null;
		article = boardDAO.selectArticles(article_no);
		return article;
	}
	
	public void modArticle(ArticleVO article) {
		boardDAO.updateArticle(article);
	}
	
	public List<Integer> removeArticle(int article_no){
		List<Integer> article_no_list = boardDAO.selectRemoveArticles(article_no);
		System.out.println("1");
		boardDAO.deleteArticle(article_no);
		System.out.println("2");
		return article_no_list;
	}
	public int addReply(ArticleVO article) {
		return boardDAO.insertNewReply(article);
	}
}
