import { Link } from "react-router-dom";

export default function NotFound() {
  return (
    <div className="text-center space-y-4 py-20">
      <h1 className="text-3xl font-bold text-red-400">404 — Page Not Found</h1>
      <p className="text-neutral-400">The page you’re looking for doesn’t exist.</p>
      <Link
        to="/"
        className="inline-block px-4 py-2 rounded-lg bg-neutral-700 text-white font-medium hover:bg-slate-200 hover:text-neutral-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-indigo-400"
      >
        Go Home
      </Link>
    </div>
  );
}