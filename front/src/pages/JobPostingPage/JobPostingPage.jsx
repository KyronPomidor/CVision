import { useEffect, useState } from "react";
import { useParams, useOutletContext } from "react-router-dom";
import styles from "./JobPostingPage.module.css";
import JobHeader from "./components/JobHeader/JobHeader";
import JobDescription from "./components/JobDescription/JobDescription";
import Sidebar from "./components/Sidebar/Sidebar";
import GalleryCard from "../../components/GalleryCard/GalleryCard";
import JobOffersCard from "../../components/JobOffersCard/JobOffersCard";
import {
  getJobPosting,
  getCurrentCompanyPostings,
  getMatchedPostings,
  getPostingApplicants,
} from "../../api/postings";
import { createApplication, getCurrentApplication } from "../../api/applications";
import { getCurrentCV } from "../../api/cv";
import { errorMessage, optionalResource } from "../../api/client";
import { mapPosting } from "../../api/mappers";

function JobPostingPage() {
  const { jobId } = useParams();
  const { user } = useOutletContext();
  const [job, setJob] = useState(null);
  const [companyJobs, setCompanyJobs] = useState([]);
  const [applicants, setApplicants] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [applying, setApplying] = useState(false);
  const [currentApplication, setCurrentApplication] = useState(null);
  const [message, setMessage] = useState(null);

  useEffect(() => {
    let active = true;
    async function load() {
      const posting = mapPosting(await getJobPosting(jobId));
      if (active) setJob(posting);
      if (user.role === "EMPLOYER" && posting.company.userId === user.id) {
        const [others, applications] = await Promise.all([
          getCurrentCompanyPostings(),
          getPostingApplicants(jobId),
        ]);
        if (active) {
          setCompanyJobs(others.filter((item) => item.id !== posting.id).map(mapPosting));
          setApplicants(applications);
        }
      } else if (user.role === "EMPLOYEE") {
        const [matches, application] = await Promise.all([
          getMatchedPostings(),
          optionalResource(getCurrentApplication()),
        ]);
        if (active) setCurrentApplication(application);
        if (active)
          setCompanyJobs(
            matches
              .filter((item) => item.company.id === posting.company.id && item.id !== posting.id)
              .map(mapPosting),
          );
      }
    }
    load()
      .catch((err) => {
        if (active) setError(err);
      })
      .finally(() => {
        if (active) setLoading(false);
      });
    return () => {
      active = false;
    };
  }, [jobId, user.id, user.role]);

  async function apply() {
    setApplying(true);
    setMessage(null);
    try {
      const cv = await getCurrentCV();
      setCurrentApplication(
        await createApplication({ jobPostingId: job.id, cvId: cv.id, status: "APPLIED" }),
      );
      setMessage("Application submitted.");
    } catch (err) {
      setMessage(
        err.response?.status === 404
          ? "Upload a CV on your profile before applying."
          : errorMessage(err),
      );
    } finally {
      setApplying(false);
    }
  }

  if (loading) return <p>Loading...</p>;
  if (error) return <p role="alert">{errorMessage(error)}</p>;
  if (!job) return <p>Job not found.</p>;

  return (
    <div className={styles.page}>
      <JobHeader
        className={`${styles.card} ${styles.jobHeader}`}
        logo={job.logo}
        jobName={job.title}
        companyName={job.company.name}
        companyId={job.company.id}
      />
      <JobDescription
        className={`${styles.card} ${styles.jobDescription}`}
        skills={job.skills}
        onApply={user.role === "EMPLOYEE" ? apply : undefined}
        applying={applying}
        applied={currentApplication?.jobPostingId === job.id || job.status === "CLOSED"}
        description={job.description}
      />
      <Sidebar className={`${styles.sidebar}`} data={job.details} perks={[]} />
      {job.gallery.length > 0 && (
        <GalleryCard className={styles.companyGallery} gallery={job.gallery} />
      )}
      {message && <p role="status">{message}</p>}
      {user.role === "EMPLOYER" && job.company.userId === user.id && (
        <section>
          <h2>Applications</h2>
          {applicants === null ? (
            <p>Loading applications...</p>
          ) : applicants.length ? (
            applicants.map((application) => (
              <p key={application.id}>
                Candidate #{application.userId} � {application.status}
              </p>
            ))
          ) : (
            <p>No applications yet.</p>
          )}
        </section>
      )}
      {companyJobs.length > 0 && (
        <JobOffersCard
          className={`${styles.card} ${styles.jobOffers} `}
          name={job.company.name}
          logo={job.logo}
          jobs={companyJobs}
        />
      )}
    </div>
  );
}

function JobPostingRoute() {
  const { jobId } = useParams();
  return <JobPostingPage key={jobId} />;
}

export default JobPostingRoute;
