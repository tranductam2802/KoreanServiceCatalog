package gdg.nat.ksc.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categories implements Serializable {
	private static final long serialVersionUID = 7149379292347412955L;

	private List<Category> listCategories = new ArrayList<Category>();

	public Categories() {
	}

	public List<Category> getListCategories() {
		return listCategories;
	}

	public Category getCategories(String id) {
		for (Category category : listCategories) {
			for (Category subCategory : category.getSubCategories()) {
				if (subCategory.getId().equals(id)) {
					return subCategory;
				}
			}
			if (category.getId().equals(id)) {
				return category;
			}
		}
		return null;
	}

	public List<Category> getListCategories(String id) {
		for (Category category : listCategories) {
			if (category.getId().equals(id)) {
				return category.getSubCategories();
			}
		}
		return new ArrayList<Category>();
	}

	public void setListCategories(List<Category> listCategories) {
		if (listCategories != null) {
			this.listCategories = listCategories;
		}
	}

	public void add(Category category) {
		if (category != null) {
			this.listCategories.add(category);
		}
	}
}