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

  const [requested, setRequested] = useState({ key: "", page: 0 });
  const [pagination, setPagination] = useState({ page: 0, totalPages: 0 });
  const queryParams = new URLSearchParams({ q: params.get("q") || "", sort });
  for (const [label, key] of [["Job Type", "jobType"], ["Schedule", "schedule"], ["Work Location", "workSetting"]]) {
    for (const value of filters[label] || []) queryParams.append(key, value);
  }
  const key = queryParams.toString();
  const page = requested.key === key ? requested.page : 0;
  const employee = user.role === "EMPLOYEE";
  const matchQuery = employee ? key + "&page=" + page : "";

  useEffect(() => {
    let active = true;
    const request = employee ? getMatchedPostings(matchQuery) : getCurrentCompanyPostings();
    request
      .then((data) => {
        if (!active) return;
        if (employee) setPagination({ page: data.page, totalPages: data.totalPages });
        setJobs((employee ? data.items : data).map(mapPosting));
      })
      .catch((err) => {
        if (active) setError(err);
      });
    return () => {
      active = false;
    };
  }, [employee, matchQuery]);

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
      >
        {employee && (
          <nav aria-label="Postings pages" className={styles.pagination}>
            <button disabled={pagination.page === 0} onClick={() => setRequested({ key, page: pagination.page - 1 })}>Previous</button>
            <span>{pagination.totalPages ? `Page ${pagination.page + 1} of ${pagination.totalPages}` : "No pages"}</span>
            <button disabled={pagination.page + 1 >= pagination.totalPages} onClick={() => setRequested({ key, page: pagination.page + 1 })}>Next</button>
          </nav>
        )}
      </RecommandCard>
      {!visibleJobs.length && <p>No matching opportunities.</p>}

      <FilterCard onSortChange={setSort} onFilterChange={changeFilter} />
    </div>
  );
}

export default HomePage;
