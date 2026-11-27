import { useEffect, useState } from "react";
import { useParams, useOutletContext } from "react-router-dom";
import styles from "./CompanyProfilePage.module.css";
import CompanyHeaderCard from "./components/CompanyHeaderCard/CompanyHeaderCard";
import AboutCard from "./components/AboutCard/AboutCard";
import CompanyDetailsCard from "./components/CompanyDetailsCard/CompanyDetailsCard";
import JobOffersCard from "../../components/JobOffersCard/JobOffersCard";
import GalleryCard from "../../components/GalleryCard/GalleryCard";
import { getCompany } from "../../api/companies";
import { getCompanyMatches, getCurrentCompanyPostings } from "../../api/postings";
import { mapCompany, mapPosting } from "../../api/mappers";
import { errorMessage } from "../../api/client";

function CompanyProfilePage() {
  const { companyId } = useParams();
  const { user } = useOutletContext();
  const [company, setCompany] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let active = true;
    async function load() {
      const data = mapCompany(await getCompany(companyId));
      let postings = [];
      if (user.role === "EMPLOYEE") postings = await getCompanyMatches(companyId);
      else if (data.userId === user.id) postings = await getCurrentCompanyPostings();
      if (active)
        setCompany({
          ...data,
          perks: [],
          jobs: postings
            .filter((posting) => String(posting.company.id) === companyId)
            .map(mapPosting),
        });
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
  }, [companyId, user.id, user.role]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p role="alert">{errorMessage(error)}</p>;

  return (
    <div className={styles.CompanyProfilePage}>
      <div className={styles.column}>
        <CompanyHeaderCard name={company.name} logo={company.logo} category={company.category} />
        <AboutCard name={company.name} description={company.description} perks={company.perks} />
        {company.gallery.length > 0 && <GalleryCard gallery={company.gallery} />}
      </div>
      <div className={styles.column}>
        <CompanyDetailsCard details={company.details} />
        {company.jobs.length > 0 && (
          <JobOffersCard name={company.name} logo={company.logo} jobs={company.jobs} />
        )}
        <p>
          {user.role === "EMPLOYEE"
            ? "Postings shown are limited to your matched opportunities."
            : ""}
        </p>
      </div>
    </div>
  );
}

function CompanyProfileRoute() {
  const { companyId } = useParams();
  return <CompanyProfilePage key={companyId} />;
}

export default CompanyProfileRoute;
