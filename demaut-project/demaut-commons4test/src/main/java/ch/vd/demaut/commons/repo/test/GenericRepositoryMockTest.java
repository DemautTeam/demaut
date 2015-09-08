package ch.vd.demaut.commons.repo.test;

import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.vo.BaseValueObject;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class GenericRepositoryMockTest extends TestCase {

    @SuppressWarnings("rawtypes")
    @Mock
    private GenericRepository genericRepository;

    @Mock
    private BaseValueObject baseValueObject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        assertThat(genericRepository).isNotNull();
    }

    @SuppressWarnings("all")
    @Test
    public void should_Find_By() throws Exception {
        assertThat(genericRepository.findBy(1)).isNull();
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate_and_store() throws Exception {
        assertThat(genericRepository.validateAndStore(baseValueObject)).isNull();
    }

    @SuppressWarnings("all")
    @Test
    public void should_delete() throws Exception {
        genericRepository.delete(baseValueObject);
    }

    @SuppressWarnings("all")
    @Test
    public void should_count_all() throws Exception {
        assertThat(genericRepository.countAll()).isEqualTo(0);
    }

    @SuppressWarnings("all")
    @Test
    public void should_validate() throws Exception {
        assertThat(genericRepository.validate(baseValueObject)).isEmpty();
    }
}