import useTrendingMovies from "../hooks/useTrendingMovies";
import { useState } from "react";
import type { MovieSummary } from "../lib/types";
import SkeletonGrid from "../components/SkeletonGrid";

export default function HomePage() {

  const [win, setWin] = useState<'day'|'week'>('day');
  const { trendingMovies, error, loading, reload: loadTrendingMovies } = useTrendingMovies(win);

  const hasData = Array.isArray(trendingMovies) && trendingMovies.length > 0;

  return (
    <div className="space-y-4">
      <div className="flex items-center gap-2">
        <button
          type="button"
          className={`px-3 py-2 rounded-xl text-sm ${win === "day" ? "bg-black text-white" : "bg-gray-100"}`}
          onClick={() => setWin("day")}
        >
          Today
        </button>
        <button
          type="button"
          className={`px-3 py-2 rounded-xl text-sm ${win === "week" ? "bg-black text-white" : "bg-gray-100"}`}
          onClick={() => setWin("week")}
        >
          This Week
        </button>
      </div>

      {loading && <SkeletonGrid />}

      {error && (
        <div className="bg-red-50 border border-red-200 text-red-700 p-3 rounded-2xl flex items-center justify-between">
          <span>{error}</span>
          <button
            type="button"
            onClick={loadTrendingMovies}
            className="px-3 py-2 rounded-xl text-sm bg-transparent hover:bg-red-100 focus-visible:outline-2 focus-visible:outline-red-600"
          >
            Retry
          </button>
        </div>
      )}

      {!loading && !error && !hasData && (
        <div className="text-sm text-gray-600">No trending movies right now.</div>
      )}

      {!loading && !error && hasData && (
        <div className="md: grid grid-cols-2 gap-4">
          {trendingMovies!.map((m: MovieSummary) => (
            <div key={m.id} className="flex-col items-center justify-center rounded-2xl border border-gray-200 p-4">
              <img src={m.imageUrl}/>
              <div className="bg-gray-100 rounded-xl" />
              <div className="mt-2 text-sm font-semibold line-clamp-2">{m.title}</div>
              <div className="mt-1 text-xs text-gray-500">⭐ {m.voteAverage ?? "—"}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}