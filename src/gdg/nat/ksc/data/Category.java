package gdg.nat.ksc.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
	private static final long serialVersionUID = 5642793385068531213L;
	private String id;
	private String name;
	private String avatarId;
	private List<Category> subCategories;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public void addCate(Category category) {
		if (category != null) {
			this.subCategories.add(category);
		}
	}

	public Category(String id, String name, String avatarId) {
		this.id = id;
		this.name = name;
		this.avatarId = avatarId;
		this.subCategories = new ArrayList<Category>();
	}

	public Category(String id, String name, String avatarId,
			List<Category> subCategories) {
		this.id = id;
		this.name = name;
		this.avatarId = avatarId;
		this.subCategories = subCategories;
	}
}