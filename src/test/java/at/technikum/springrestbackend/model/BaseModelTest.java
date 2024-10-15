package at.technikum.springrestbackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;

 public class BaseModelTest {

    private static class ConcreteBaseModel extends BaseModel {
        public ConcreteBaseModel() {
            super();
        }

        public ConcreteBaseModel(String id) {
            super(id);
        }

        public ConcreteBaseModel(String id, Date createdAt, Date lastChangeAt) {
            super(id, createdAt, lastChangeAt);
        }
    }

    private ConcreteBaseModel baseModel;
    private String testId = "test-uuid";
    private Date testCreatedAt;
    private Date testLastChangeAt;

    @BeforeEach
    void setUp() {
        testCreatedAt = new Date();
        testLastChangeAt = new Date();
        baseModel = new ConcreteBaseModel();
    }

    @Test
    void testDefaultConstructor() {
        assertThat(baseModel.getId()).isNull();
        assertThat(baseModel.getCreatedAt()).isNotNull();
        assertThat(baseModel.getLastChangeAt()).isNotNull();
        assertThat(baseModel.getCreatedAt()).isCloseTo(new Date(), 1000);
        assertThat(baseModel.getLastChangeAt()).isCloseTo(new Date(), 1000);
    }

    @Test
    void testConstructorWithId() {
        baseModel = new ConcreteBaseModel(testId);
        assertThat(baseModel.getId()).isEqualTo(testId);
        assertThat(baseModel.getCreatedAt()).isNotNull();
        assertThat(baseModel.getLastChangeAt()).isNotNull();
    }

    @Test
    void testConstructorWithAllParameters() {
        baseModel = new ConcreteBaseModel(testId, testCreatedAt, testLastChangeAt);
        assertThat(baseModel.getId()).isEqualTo(testId);
        assertThat(baseModel.getCreatedAt()).isEqualTo(testCreatedAt);
        assertThat(baseModel.getLastChangeAt()).isEqualTo(testLastChangeAt);
    }

    @Test
    void testSetId() {
        baseModel.setId(testId);
        assertThat(baseModel.getId()).isEqualTo(testId);
    }

    @Test
    void testSetLastChangeAt() {
        baseModel.setLastChangeAt(testLastChangeAt);
        assertThat(baseModel.getLastChangeAt()).isEqualTo(testLastChangeAt);
    }
}
