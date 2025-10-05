export default function SkeletonGrid({ count = 10 }: { count?: number }) {
  return (
    <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
      {Array.from({ length: count }).map((_, i) => (
        <div
          key={i}
          className="rounded-2xl border border-gray-200 p-4 animate-pulse"
        >
          <div className="aspect-[2/3] bg-gray-200 rounded-xl" />
          <div className="h-4 bg-gray-200 rounded mt-3 w-4/5" />
        </div>
      ))}
    </div>
  );
}