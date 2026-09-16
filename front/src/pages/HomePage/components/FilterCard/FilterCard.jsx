import styles from "./styles/FilterCard.module.css"
import SortBy from "./SortBy"
import QuickFilters from "./QuickFilters"

const SORT_OPTIONS = ["Compatibility", "Salary", "Recency"]

const FILTER_GROUPS = [
    {
        title: "Job Type",
        options: ["Full-time Contract", "Temporary Contract", "Internship", "Volunteer"],
    },
    {
        title: "Schedule",
        options: ["Full-time", "Part-time", "Flexible"],
    },
    {
        title: "Work Location",
        options: ["Remote", "Hybrid", "On-site"],
    },
]

function FilterCard({ onSortChange, onFilterChange }) {

    return (
        <div className={styles.FilterCard}>
            <SortBy
                options={SORT_OPTIONS}
                onChange={onSortChange}
            />

            <QuickFilters
                groups={FILTER_GROUPS}
                onChange={onFilterChange}
            />
        </div>
    )
}

export default FilterCard
