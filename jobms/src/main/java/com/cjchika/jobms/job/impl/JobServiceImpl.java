package com.cjchika.jobms.job.impl;

import com.cjchika.jobms.job.Job;
import com.cjchika.jobms.job.JobRepository;
import com.cjchika.jobms.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepo;

    @Override
    public List<Job> findAll() {
        return jobRepo.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepo.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepo.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        Optional<Job> jobOptional = jobRepo.findById(id);

        if (jobOptional.isPresent()) {
            jobRepo.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateJob(Long id, Job job) {
        Optional<Job> jobOptional = jobRepo.findById(id);

        if(jobOptional.isPresent()){
            Job existingJob = jobOptional.get();

            existingJob.setTitle(job.getTitle());
            existingJob.setDescription(job.getDescription());
            existingJob.setLocation(job.getLocation());
            existingJob.setMaxSalary(job.getMaxSalary());
            existingJob.setMinSalary(job.getMinSalary());
            existingJob.setCompanyId(job.getCompanyId());

            jobRepo.save(existingJob);
            return true;
        }
        return false;
    }
}
