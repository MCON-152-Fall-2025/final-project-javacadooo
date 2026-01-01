package com.mcon152.recipeshare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.mcon152.recipeshare.web.RecipeRequest;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "recipe_type", discriminatorType = DiscriminatorType.STRING, columnDefinition = "VARCHAR(31) DEFAULT 'BASIC'")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Recipe extends BaseEntity {

    private String title;
    private String description;

    @Column(length = 2000)
    private String ingredients;

    @Column(length = 4000)
    private String instructions;

    private Integer servings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AppUser author;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_tags",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "recipe_type", insertable = false, updatable = false, nullable = true,
            columnDefinition = "VARCHAR(31) DEFAULT 'BASIC'")
    private String recipeType;

    protected abstract Recipe createFromRequestInstance(RecipeRequest req);

    protected void populateCommonFields(Recipe target, RecipeRequest req) {
        if (target == null) return;
        target.setId(null);
        if (req != null) {
            target.setTitle(req.getTitle());
            target.setDescription(req.getDescription());
            target.setIngredients(req.getIngredients());
            target.setInstructions(req.getInstructions());
            target.setServings(req.getServings());
        }
    }

    public Recipe() {}

    public Recipe(Long id, String title, String description, String ingredients, String instructions, Integer servings) {
        setId(id);
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servings = servings;
    }
    public Recipe(Long id, String title, String description, String ingredients, String instructions, Integer servings, AppUser author) {
        this(id, title, description, ingredients, instructions, servings);
        this.author = author;
    }

    // Existing Getter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    /**
     * ADD THIS METHOD: Alias for getTitle() to fix the Component errors
     */
    public String getName() {
        return this.title;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public Integer getServings() { return servings; }
    public void setServings(Integer servings) { this.servings = servings; }

    public AppUser getAuthor() { return author; }
    public void setAuthor(AppUser author) { this.author = author; }

    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getRecipes().add(this);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getRecipes().remove(this);
    }
    public void clearTags() {
        this.tags.clear();
    }

    public String getRecipeType() { return recipeType; }
}