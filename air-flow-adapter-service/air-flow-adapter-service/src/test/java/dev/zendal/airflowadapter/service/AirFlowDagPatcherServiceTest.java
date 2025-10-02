package dev.zendal.airflowadapter.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AirFlowDagPatcherServiceTest {

    @InjectMocks
    private AirFlowDagPatcherServiceImpl airFlowDagPatcherService;


    @MethodSource
    @ParameterizedTest
    void shouldInsertDagId(String source, String patched) {
        final var mockId = "ID_MOCK_HERE";
        var result = this.airFlowDagPatcherService.insertDagId(source, mockId);


        assertThat(result).isEqualTo(patched);
    }


    static Stream<Arguments> shouldInsertDagId() {
        return Stream.of(
                Arguments.of(
                        """
                                from airflow import DAG
                                from datetime import datetime
                                
                                            dag = DAG(
                                                default_args={"owner": "airflow"},
                                                schedule_interval="@daily"
                                            )
                                """,
                        """
                                from airflow import DAG
                                from datetime import datetime
                                
                                            dag = DAG(dag_id="ID_MOCK_HERE",\s
                                                default_args={"owner": "airflow"},
                                                schedule_interval="@daily"
                                            )
                                """
                ),
                Arguments.of(
                        """
                                dag = DAG(
                                    "example_dag",
                                    default_args=default_args,
                                    schedule_interval="@daily",
                                )
                                """,
                        """
                                dag = DAG(dag_id="ID_MOCK_HERE",
                                    default_args=default_args,
                                    schedule_interval="@daily",
                                )
                                """
                ),
                Arguments.of(
                        """
                                dag = DAG(
                                    dag_id="TEST",
                                    default_args=default_args,
                                    schedule_interval="@daily",
                                )
                                """,
                        """
                                dag = DAG(
                                    dag_id="ID_MOCK_HERE",
                                    default_args=default_args,
                                    schedule_interval="@daily",
                                )
                                """
                )
        );
    }

}