package org.example.visual_programming.blocks;

import java.util.List;

public abstract class AbstractBlock implements Block{
    private String id;
    private String name;
    private String description;
    private List<Block> children;

    public AbstractBlock(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<Block> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<Block> children) {
        this.children = children;
    }
}
