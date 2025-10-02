package dev.zendal.etlsetup.dto.codegenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PipelineResponse {

    private String status;

    @JsonProperty("ddl_job_id")
    private String ddlJobId;

    @JsonProperty("dag_job_id")
    private String dagJobId;

    @JsonProperty("ddl_results")
    private List<DDLResult> ddlResults;

    @JsonProperty("dag_code")
    private String dagCode;

    @JsonProperty("pipeline_visualization")
    private Map<Object, Object> pipelineVisualization;

    private String error;

    private Progress progress;

    @Data
    public static class DDLResult {
        @JsonProperty("table_name")
        private String tableName;

        private String ddl;
        private String status;
    }

    @Data
    public static class Progress {
        @JsonProperty("current_step")
        private String currentStep;

        @JsonProperty("ddl_progress")
        private String ddlProgress;

        @JsonProperty("dag_progress")
        private String dagProgress;
    }
}
