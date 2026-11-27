export function skillLabel(name, years) {
  if (years == null) return name;
  return `${name} · ${years} ${years === 1 ? "year" : "years"}`;
}
