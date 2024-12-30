package com.cjchika.jobms.job;

import com.cjchika.jobms.job.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){
        List<JobDTO> jobs = jobService.findAll();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>(job, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> createJob(@PathVariable Long id){
        JobDTO jobDTO = jobService.getJobById(id);

        if(jobDTO != null)
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean isDeleted = jobService.deleteJobById(id);

        if(isDeleted)
            return new ResponseEntity<>("Job deleted successfully!", HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job){
        boolean updated = jobService.updateJob(id, job);

        if(updated)
            return new ResponseEntity<>("Job updated successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Job not found!",HttpStatus.NOT_FOUND);
    }
}
