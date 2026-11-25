import { useEffect, useState } from "react";
import { useNavigate, useOutletContext, useSearchParams } from "react-router-dom";
import styles from "./HomePage.module.css";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import RecommandCard from "./components/RecommandCard/RecommandCard";
import FilterCard from "./components/FilterCard/FilterCard";
import { getMatchedPostings, getCurrentCompanyPostings } from "../../api/postings";
import { mapPosting } from "../../api/mappers";
import { errorMessage } from "../../api/client";

function HomePage() {
  const { user, profile } = useOutletContext();
  const navigate = useNavigate();
  const [params] = useSearchParams();
  const [jobs, setJobs] = useState(null);
  const [error, setError] = useState(null);
  const [sort, setSort] = useState("Compatibility");
  const [filters, setFilters] = useState({});

  useEffect(() => {
    let active = true;
    const request = user.role === "EMPLOYEE" ? getMatchedPostings() : getCurrentCompanyPostings();
    request
      .then((data) => {
        if (active) setJobs(data.map(mapPosting));
      })
      .catch((err) => {
        if (active) setError(err);
      });
    return () => {
      active = false;
    };
  }, [user.role]);

  function changeFilter(group, value, checked) {
    setFilters((previous) => ({
      ...previous,
      [group]: checked
        ? [...(previous[group] || []), value]
        : (previous[group] || []).filter((item) => item !== value),
    }));
  }

  if (error) return <p role="alert">{errorMessage(error)}</p>;
  if (!jobs) return <p>Loading...</p>;

  const query = (params.get("q") || "").toLowerCase();
  const visibleJobs = jobs
    .filter(
      (job) =>
        `${job.title} ${job.company?.name} ${job.details.location}`.toLowerCase().includes(query) &&
        Object.entries(filters).every(
          ([group, values]) =>
            !values.length ||
            values.includes(
              job.details[
                { "Job Type": "jobType", Schedule: "schedule", "Work Location": "workSetting" }[
                  group
                ]
              ],
            ),
        ),
    )
    .sort((a, b) => {
      if (sort === "Salary") return (b.details.salary ?? -1) - (a.details.salary ?? -1);
      if (sort === "Recency") return new Date(b.createdAt) - new Date(a.createdAt);
      return (b.matchPercent ?? -1) - (a.matchPercent ?? -1);
    })
    .map((job) => ({ ...job, onApply: () => navigate(`/jobs/${job.id}`) }));

  return (
    <div className={styles.HomePage}>
      <div>
        {profile && (
          <ProfileCard
            variant="home"
            name={profile.name}
            photo={profile.photo}
            email={profile.email}
            phone={profile.phone}
            location={profile.location}
            aboutMe={profile.aboutMe}
            appliedJobs={profile.appliedJobs}
            savedJobs={profile.savedJobs}
            cvViews={profile.cvViews}
            reachouts={profile.reachouts}
            onEditProfile={() => navigate("/profile?edit=true")}
          />
        )}
        {user.role === "EMPLOYER" && <p>Your company postings</p>}
      </div>

      <RecommandCard
        jobs={visibleJobs}
        title={user.role === "EMPLOYER" ? "Your company postings" : "Recommended Opportunities"}
        actionLabel="View job"
      />
      {!visibleJobs.length && <p>No matching opportunities.</p>}

      <FilterCard onSortChange={setSort} onFilterChange={changeFilter} />
    </div>
  );
}

export default HomePage;
