import { mediaUrl } from "./client";

export function mapProfile(profile, user) {
  return {
    ...profile,
    name: profile?.name || user.accountName,
    email: user.email,
    photo: mediaUrl(profile?.pictureUrl),
    aboutMe: profile?.description,
  };
}

export function mapCompany(company) {
  const details = company.details || {};
  return {
    ...company,
    logo: mediaUrl(company.pictureUrl || company.logo),
    gallery: (company.photos || []).map((src) => ({ src: mediaUrl(src), alt: company.name })),
    details: {
      ...details,
      socials: {
        linkedin: details.linkedin,
        instagram: details.instagram,
        facebook: details.facebook,
        twitter: details.twitter,
      },
    },
  };
}

export function mapPosting(posting) {
  const details = posting.details || {};
  return {
    ...posting,
    company: posting.company ? mapCompany(posting.company) : null,
    skills: (posting.skills || []).map((skill) => skill.name),
    gallery: (posting.images || []).map((image) => ({
      src: mediaUrl(image.url),
      alt: posting.title,
    })),
    details,
    logo: mediaUrl(posting.company?.pictureUrl || posting.company?.logo),
    postedAt: posting.createdAt,
    employmentType: details.jobType,
    schedule: details.schedule,
    salary: details.salary == null ? undefined : String(details.salary),
    experience: details.experience == null ? undefined : `${details.experience} years`,
    matchPercent: posting.matchScore == null ? null : Math.round(posting.matchScore),
  };
}

export function mapCVInformation(cv, profile) {
  return {
    skills: cv ? cv.skills || [] : (profile?.skills || []).map((skill) => skill.skillName),
    languages: Array.isArray(cv?.languages) ? cv.languages : [],
    objective: cv?.objective || "",
    experience: Array.isArray(cv?.experience) ? cv.experience : [],
    additionalInfo: cv?.additionalInfo || "",
  };
}
