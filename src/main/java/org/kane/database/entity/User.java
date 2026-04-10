package org.kane.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.PathConverter;
import org.kane.database.entity.diary.DiaryRecord;
import org.kane.database.entity.diary.Task;
import org.kane.database.entity.diary.WeightRecord;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.enum_types.Gender;
import org.kane.database.enum_types.Role;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column(name="height_sm")
    private Short height;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(name ="birthdate")
    private LocalDate birthdate;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private ImageModel avatar;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @Builder.Default
    private List<WeightRecord> records = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @Builder.Default
    @Size(max = 10)
    private List<Task> tasks = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "favourite_recipe",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="recipe_id")
    )
    @Builder.Default
    private Set<Recipe> favouriteRecipes = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    @Builder.Default
    private List<DiaryRecord> diaryRecords = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "author_id")
    @Builder.Default
    private List<NutritionalInfo> authorBy = new ArrayList<>();

    public void addRecord(WeightRecord rec) {
        this.records.add(rec);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addFavouriteRecipe(Recipe recipe) {
        this.favouriteRecipes.add(recipe);
    }

    public void addDiaryRecord(DiaryRecord diaryRecord) {
        this.diaryRecords.add(diaryRecord);
    }

    public void addNewNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.authorBy.add(nutritionalInfo);
        nutritionalInfo.setAuthor(this);
    }

    public List<Product> getAuthorProducts() {
        return this.authorBy.stream()
                .filter(Product.class::isInstance)
                .map(n->(Product)n)
                .toList();
    }

    public List<Recipe> getAuthorRecipes() {
        return this.authorBy.stream()
                .filter(Recipe.class::isInstance)
                .map(n->(Recipe)n)
                .toList();
    }
}
