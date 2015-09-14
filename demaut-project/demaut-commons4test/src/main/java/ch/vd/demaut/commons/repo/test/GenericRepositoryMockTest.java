package ch.vd.demaut.commons.repo.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ch.vd.demaut.commons.repo.GenericRepository;
import ch.vd.demaut.commons.vo.BaseValueObject;
import junit.framework.TestCase;

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

    //TODO: Add tests
}