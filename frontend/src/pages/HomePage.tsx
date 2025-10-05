import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
      {[...Array(6)].map((_, i) => (
        <Link key={i} to={`/movies/${i + 1}`} className="block">
          <div className="h-40 rounded-2xl bg-neutral-900 animate-pulse" />
        </Link>
      ))}
    </div>
  );
}