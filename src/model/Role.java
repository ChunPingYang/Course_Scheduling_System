package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "role")
// TODO: Check UniqueEntity
public class Role implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    //minlength
    @Column(name = "name", length = 32, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> user;

    @ManyToOne
    @JoinColumn(name = "last_modified_by", nullable = true, columnDefinition = "CHAR(36)", referencedColumnName = "id")
    private User lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = true, columnDefinition = "CHAR(36)", referencedColumnName = "id")
    private User createdBy;

    @Column(name = "last_modified_on", nullable = true)
    private Date lastModifiedOn;

    @Column(name = "created_on", nullable = true)
    private Date createdOn;

}
