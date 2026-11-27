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
    skills: posting.skills || [],
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
  const profileSkills = profile?.skills || [];
  const years = new Map([
    ...profileSkills.map((skill) => [skill.skillName.toLowerCase(), skill.yearsOfExperience]),
    ...(cv?.skillExperience || []).map((skill) => [skill.name.toLowerCase(), skill.yearsOfExperience]),
  ]);
  const names = cv
    ? [...(cv.skills || []), ...(cv.skillExperience || []).map((skill) => skill.name)]
    : profileSkills.map((skill) => skill.skillName);
  const skills = new Map();
  for (const name of names) {
    const key = name.toLowerCase();
    if (!skills.has(key)) skills.set(key, { name, yearsOfExperience: years.get(key) });
  }
  return {
    skillExperience: [...skills.values()],
    languages: Array.isArray(cv?.languages) ? cv.languages : [],
    objective: cv?.objective || "",
    experience: Array.isArray(cv?.experience) ? cv.experience : [],
    additionalInfo: cv?.additionalInfo || "",
  };
}
